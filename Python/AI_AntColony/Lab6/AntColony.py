import random as rn
import numpy as np
from numpy.random import choice as np_choice
import random
import math
import matplotlib.pyplot as plt


class AntColony(object):

    def __init__(self, distances, n_ants, n_best, n_iterations, decay, alpha=1, beta=1):
        self.distances = distances
        self.pheromone = np.ones(self.distances.shape) / len(distances)
        self.all_inds = range(len(distances))
        self.n_ants = n_ants
        self.n_best = n_best
        self.n_iterations = n_iterations
        self.decay = decay
        self.alpha = alpha
        self.beta = beta
        self.best = []

    def return_best(self):
        return self.best

    def run(self):
        shortest_path = None
        best = []
        all_time_shortest_path = ("placeholder", np.inf)
        for i in range(self.n_iterations):
            all_paths = self.gen_all_paths()
            self.spread_pheronome(all_paths, self.n_best, shortest_path=shortest_path)
            shortest_path = min(all_paths, key=lambda x: x[1])
            print(shortest_path)
            self.best.append(shortest_path[1])
            if shortest_path[1] < all_time_shortest_path[1]:
                all_time_shortest_path = shortest_path
            self.pheromone * self.decay
        return all_time_shortest_path

    def spread_pheronome(self, all_paths, n_best, shortest_path):
        sorted_paths = sorted(all_paths, key=lambda x: x[1])
        for path, dist in sorted_paths[:n_best]:
            for move in path:
                self.pheromone[move] += 1.0 / self.distances[move]

    def gen_path_dist(self, path):
        total_dist = 0
        for ele in path:
            total_dist += self.distances[ele]
        return total_dist

    def gen_all_paths(self):
        all_paths = []
        for i in range(self.n_ants):
            path = self.gen_path(0)
            all_paths.append((path, self.gen_path_dist(path)))
        return all_paths

    def gen_path(self, start):
        path = []
        visited = set()
        visited.add(start)
        prev = start
        for i in range(len(self.distances) - 1):
            move = self.pick_move(self.pheromone[prev], self.distances[prev], visited)
            path.append((prev, move))
            prev = move
            visited.add(move)
        path.append((prev, start))
        return path

    def pick_move(self, pheromone, dist, visited):
        pheromone = np.copy(pheromone)
        pheromone[list(visited)] = 0

        row = pheromone ** self.alpha * ((1.0 / dist) ** self.beta)

        norm_row = row / row.sum()
        move = np_choice(self.all_inds, 1, p=norm_row)[0]
        return move


class Point:

    def __init__(self, name, x, y):
        self.name = name
        self.x = x
        self.y = y

    def get_name(self):
        return self.name

    def get_x(self):
        return self.x

    def get_y(self):
        return self.y


def generatePoints(n):
    graph = []
    for i in range(0, n):
        x = random.randrange(1, 100)
        y = random.randrange(1, 100)
        p = Point(i, x, y)
        graph.append(p)
    return graph


def generateDistances(graph):
    weights = []
    for i in range(0, len(graph)):
        row = []
        for j in range(0, len(graph)):
            row.append('_')
        weights.append(row)
    for i in range(0, len(graph)):
        weights[i][i] = np.inf
    for i in range(0, len(graph)):
        for j in range(0, len(graph)):
            if (i != j):
                n1 = (graph[i].get_x() - graph[j].get_x()) * (graph[i].get_x() - graph[j].get_x())
                n2 = (graph[i].get_y() - graph[j].get_y()) * (graph[i].get_y() - graph[j].get_y())
                weights[i][j] = math.sqrt(n1 + n2)
    with open('distances.txt', 'wb') as f:
        np.savetxt(f, weights, fmt='%.0f')

    return np.array(weights)


Points = generatePoints(100)
Distances = generateDistances(Points)

ant_colony = AntColony(Distances, 150, 75, 10, 2, alpha=1, beta=1)
shortest_path = ant_colony.run()
dest = ant_colony.return_best()
print("shorted_path: {}".format(shortest_path))
Time = []
for i in range(0, len(dest)):
    Time.append(i)

plt.plot(Time, dest)
plt.xlabel('Time')
plt.ylabel('Best')
plt.show()
