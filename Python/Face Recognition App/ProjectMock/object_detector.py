import os
import random
import tensorflow as tf
from tensorflow import keras
from tensorflow.keras.preprocessing.image import img_to_array
from imutils.object_detection import non_max_suppression
from pyimagesearch.detection_helpers import sliding_window
from pyimagesearch.detection_helpers import image_pyramid
import numpy as np
import imutils
import cv2


# reads the images from datasource and puts them in a list with their labels
def create_training_data(datasource):
    IMG_SIZE = 50
    DATA = datasource
    CATEGORIES = ['Delia', 'Daniela', 'Paul']
    training_data = []
    training_labels = []
    aux = []
    for category in CATEGORIES:
        path = os.path.join(DATA, category)
        class_num = CATEGORIES.index(category)
        for img in os.listdir(path):
            img_array = cv2.imread(os.path.join(path, img), cv2.IMREAD_GRAYSCALE)
            new_array = cv2.resize(img_array, (IMG_SIZE, IMG_SIZE))
            aux.append([new_array, class_num])
    random.shuffle(aux)
    for i in range(len(aux)):
        training_data.append(aux[i][0])
        training_labels.append(aux[i][1])
    training_data = np.array(training_data)
    training_labels = np.array(training_labels)
    return (training_data, training_labels)


# formats the predictions (true label, confidence)
def decode_predictions(predictions):
    new_predictions = []
    for pred in predictions:
        max_pred = max(pred)
        pos = 0
        while pred[pos] != max_pred:
            pos += 1
        new_predictions.append((class_names[pos], max_pred))
    return new_predictions


# initializes variables
size = '(300, 300)'
min_conf = 0.7
visualize = 1
width = 500
pyr_scale = 1.5
win_step = 16
roi_size = eval(size)
input_size = (50, 50)

# creates the lists for training and test
(train_images, train_labels) = create_training_data("Images/Training")
(test_images, test_labels) = create_training_data("Images/Test")

# the names of the classes
class_names = ['Delia', 'Daniela', 'Paul']

# formats the lists to contain values between 0 and 1
train_images = train_images / 255.0
test_images = test_images / 255.0

# creates and trains the model
model = keras.Sequential(
    [keras.layers.Flatten(input_shape=(50, 50)), keras.layers.Dense(8, activation='relu'), keras.layers.Dense(3)])
model.compile(optimizer='adam', loss=tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True),
              metrics=['accuracy'])
model.fit(train_images, train_labels, epochs=5)

# takes the first (random) image of test_images, resizes it and saves its dimensions
orig = test_images[0]
orig = imutils.resize(orig, width=width)
(H, W) = orig.shape[:2]

# initialize the image pyramid
pyramid = image_pyramid(orig, scale=pyr_scale, minSize=roi_size)

rois = []  # generated ROIs
locs = []  # coordinates of the generated ROIs

for image in pyramid:

    # determines the scale factor between the *original* image dimensions and the *current* layer of the pyramid
    scale = W / float(image.shape[1])

    # for each layer of the image pyramid, loops over the sliding window locations
    for (x, y, roiOrig) in sliding_window(image, win_step, roi_size):

        # scales the (x, y)-coordinates of the ROI respecting to the *original* image dimensions
        x = int(x * scale)
        y = int(y * scale)
        w = int(roi_size[0] * scale)
        h = int(roi_size[1] * scale)

        # takes the ROI and preprocesses it
        roi = cv2.resize(roiOrig, input_size)
        roi = img_to_array(roi)

        # updates our list of ROIs and associated coordinates
        rois.append(roi)
        locs.append((x, y, x + w, y + h))

        # checks if we are visualizing each of the sliding windows in the image pyramid
        if visualize > 0:
            # clones the original image and surrounds the current region with a bounding box
            clone = orig.copy()
            cv2.rectangle(clone, (x, y), (x + w, y + h),
                          (0, 255, 0), 2)
rois = np.array(rois)

# makes predictions
probability_model = tf.keras.Sequential([model, tf.keras.layers.Softmax()])
predictions = probability_model.predict(rois)

# decodes the predictions
preds = decode_predictions(predictions)
labels = {}

for (i, p) in enumerate(preds):

    # grabs the prediction information for the current ROI
    (label, prob) = p
    if prob >= min_conf:
        # grabs the bounding box associated with the prediction and converts the coordinates
        box = locs[i]

        # grabs the list of predictions for the label and adds the bounding box and probability to the list
        L = labels.get(label, [])
        L.append((box, prob))
        labels[label] = L

# loops over the labels for each detected object in the image
for label in labels.keys():

    # clones the original image, so that we can draw on it
    print("Results for '{}'".format(label))
    print(labels[label][0])
    clone = orig.copy()

    # loops over all bounding boxes for the current label
    for (box, prob) in labels[label]:
        # draws the bounding box on the image
        (startX, startY, endX, endY) = box
        cv2.rectangle(clone, (startX, startY), (endX, endY),
                      (0, 255, 0), 2)
    clone = orig.copy()

    # extracts the bounding boxes and associated prediction probabilities and applies non-maxima suppression
    boxes = np.array([p[0] for p in labels[label]])
    proba = np.array([p[1] for p in labels[label]])
    boxes = non_max_suppression(boxes, proba)

    # loops over all bounding boxes that were kept after applying non-maxima suppression
    for (startX, startY, endX, endY) in boxes:
        # draws the bounding box and label on the image
        cv2.rectangle(clone, (startX, startY), (endX, endY),
                      (0, 255, 0), 2)
        y = startY - 10 if startY - 10 > 10 else startY + 10
        cv2.putText(clone, label, (startX, y),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.45, (0, 255, 0), 2)

    # shows the result
    cv2.imshow("Result", clone)
    cv2.waitKey(0)
