import random
import tensorflow as tf
from tensorflow import keras
import numpy as np
import matplotlib.pyplot as plt
import cv2
import os


# reads the images from datasource and puts them in a list with their labels
def create_data(datasource):
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


# plots an image with its predicted label, confidence and true label
def plot_image(i, predictions_array, true_label, img):
    predictions_array, true_label, img = predictions_array, true_label[i], img[i]
    plt.grid(False)
    plt.xticks([])
    plt.yticks([])

    plt.imshow(img, cmap=plt.cm.binary)

    predicted_label = np.argmax(predictions_array)
    if predicted_label == true_label:
        color = 'green'
    else:
        color = 'red'

    plt.xlabel("{} {:2.0f}% ({})".format(class_names[predicted_label],
                                         100 * np.max(predictions_array),
                                         class_names[true_label]),
               color=color)


# plots a diagram with the predictions for each class for an image
def plot_value_array(i, predictions_array, true_label):
    predictions_array, true_label = predictions_array, true_label[i]
    plt.grid(False)
    plt.xticks(range(3))
    plt.yticks([])
    thisplot = plt.bar(range(3), predictions_array, color="#777777")
    plt.ylim([0, 1])
    predicted_label = np.argmax(predictions_array)

    thisplot[predicted_label].set_color('red')
    thisplot[true_label].set_color('green')


# gets nr random test images
def get_random_images(nr):
    random = np.random.randint(0, len(test_labels), nr)
    shuffle_images = []
    shuffle_labels = []
    for i in range(len(random)):
        shuffle_images.append(test_images[random[i]])
        shuffle_labels.append(test_labels[random[i]])
    shuffle_images = np.array(shuffle_images)
    shuffle_labels = np.array(shuffle_labels)
    return (shuffle_images, shuffle_labels)


# creates the lists for training and test
(train_images, train_labels) = create_data("Images/Training")
(test_images, test_labels) = create_data("Images/Test")

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
model.fit(train_images, train_labels, epochs=10)

# makes predictions
probability_model = tf.keras.Sequential([model, tf.keras.layers.Softmax()])
predictions = probability_model.predict(test_images)

# plots the first X test images, their predicted labels, and the true labels.
# colors correct predictions in green and incorrect predictions in red.
num_rows = 3
num_cols = 3
num_images = num_rows * num_cols
(test_images, test_labels) = get_random_images(num_images)
plt.figure(figsize=(2 * 2 * num_cols, 2 * num_rows))
for i in range(num_images):
    plt.subplot(num_rows, 2 * num_cols, 2 * i + 1)
    plot_image(i, predictions[i], test_labels, test_images)
    plt.subplot(num_rows, 2 * num_cols, 2 * i + 2)
    plot_value_array(i, predictions[i], test_labels)
plt.tight_layout()
plt.show()
