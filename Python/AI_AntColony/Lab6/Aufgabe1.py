import math
import random
import numpy as np
import matplotlib.pyplot as plt


class Gluhwurmchen:

    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.count = 0

    def get_x(self):
        return self.x

    def get_y(self):
        return self.y

    def get_count(self):
        return self.count

    def set_count(self, count):
        self.count = count


def generatePoints(n):
    schwarm = []
    for i in range(0, n):
        x = random.randrange(0, 10)
        y = random.randrange(0, 10)
        g = Gluhwurmchen(x, y)
        schwarm.append(g)
    return schwarm


def generateDistances(schwarm):
    weights = []
    for i in range(0, len(schwarm)):
        row = []
        for j in range(0, len(schwarm)):
            row.append('_')
        weights.append(row)
    for i in range(0, len(schwarm)):
        for j in range(0, len(schwarm)):
            n1 = (schwarm[i].get_x() - schwarm[j].get_x()) * (schwarm[i].get_x() - schwarm[j].get_x())
            n2 = (schwarm[i].get_y() - schwarm[j].get_y()) * (schwarm[i].get_y() - schwarm[j].get_y())
            weights[i][j] = math.sqrt(n1 + n2)
    with open('distances.txt', 'wb') as f:
        np.savetxt(f, weights, fmt='%.2f')
    return weights


# Counts the neighbours of glowworm nr.
# nr - position of glowworm in list
# n - total number of glowworms
# r - neighbourhoodradius
def get_neighbours(nr):
    neighbours = []
    for i in range(0, n):
        if distances[nr][i] <= r:
            neighbours.append(i)
    return neighbours


def is_sync(nr):
    sync = 0
    neighbours = get_neighbours(nr)
    for nb in neighbours:
        if schwarm[nb].get_count() < 26:
            sync += 1
    return sync > len(neighbours) / 2


def zeitschritt():
    for i in range(0, n):
        if schwarm[i].get_count() != 0:
            if schwarm[i].get_count() == 50:
                if not (is_sync(i)):
                    schwarm[i].set_count(2)
                else:
                    schwarm[i].set_count(1)
            else:
                schwarm[i].set_count(schwarm[i].get_count() + 1)


# Activates glowworm nr
def release_glowworm(nr):
    zeitschritt()
    schwarm[nr].set_count(1)


# --------------------- Teil A ---------------------------
n = 150
schwarm = generatePoints(n)
distances = generateDistances(schwarm)
r = 0.05
total = 0
for i in range(0, n):
    nachbarn = get_neighbours(i)
    total += len(nachbarn)
print('Durchschnittliche Anzahl von Nachbarn: ', total / n)

for i in range(0, n):
    release_glowworm(i)
leuchtend = []
zeitschritte = []
for i in range(0, 5000):
    zeitschritt()
    anz_leuchtend = 0
    for gw in schwarm:
        if gw.get_count() < 26:
            anz_leuchtend += 1
    leuchtend.append(anz_leuchtend)
    zeitschritte.append(i)

plt.plot(zeitschritte, leuchtend)
plt.xlabel('Zeitschritte')
plt.ylabel('Anzahl leuchtende Gluhwurmchen')
plt.axis([0, 5000, 0, 150])
plt.show()

# --------------------- Teil B ----------------------
totale_amplitude = 0
alle_amplituden = []
r = 0.025
radien = []
for i in range(0, 50):
    radien.append(r)
    schwarm = generatePoints(n)
    distances = generateDistances(schwarm)
    for j in range(0, n):
        release_glowworm(j)
    last = []
    for t in range(0, 5000):
        zeitschritt()
        if t >= 4950:
            leuchtend = 0
            for gw in schwarm:
                if gw.get_count() < 26:
                    leuchtend += 1
            last.append(leuchtend)
    amplitude = (max(last) - min(last)) / 2
    alle_amplituden.append(amplitude)
    totale_amplitude += amplitude
    r += 0.025

print('Durchschnitt der gemessenen Amplituden: ', totale_amplitude / 50)

plt.plot(radien, alle_amplituden)
plt.xlabel('Radius')
plt.ylabel('Amplitude')
plt.show()
