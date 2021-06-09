import random
import math
import numpy as np


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


def invertierendeMutation(A):
    B = []
    for i in range(0, len(A)):
        B.append(A[i])
    u1 = random.randrange(0, len(A) - 1)
    u2 = random.randrange(0, len(A) - 1)
    if u1 > u2:
        aux = u1
        u1 = u2
        u2 = aux
    for j in range(u1, u2 + 1):
        B[u2 + u1 - j] = A[j]
    return B


def zielfunktion(A, matrix):
    sum = 0
    for i in range(0, len(A) - 1):
        sum += int(matrix[A[i].get_name()][A[i + 1].get_name()])
    sum += int(matrix[A[0].get_name()][A[len(A) - 1].get_name()])
    return sum


def best(P):
    for i in range(0, len(P) - 1):
        for j in range(i + 1, len(P)):
            if P[i][1] > P[j][1]:
                aux = P[i]
                P[i] = P[j]
                P[j] = aux
    list = P[0:10]
    return list


def kantenrekombination(A, B):
    adj = []
    num = []
    for i in range(0, len(A)):
        adj.append([])
        num.append(i)
    for i in range(0, len(A) - 1):
        if not (A[i % len(A) + 1].get_name() in adj[A[i].get_name()]):
            adj[A[i].get_name()].append(A[i % len(A) + 1].get_name())
            adj[A[i % len(A) + 1].get_name()].append(A[i].get_name())
        if not (B[i % len(B) + 1].get_name() in adj[B[i].get_name()]):
            adj[B[i].get_name()].append(B[i % len(B) + 1].get_name())
            adj[B[i % len(B) + 1].get_name()].append(B[i].get_name())
    c = []
    c.append(random.randint(0, len(A) - 1))
    num.remove(c[0])
    for i in adj[c[0]]:
        adj[i].remove(c[0])
    for i in range(0, len(A) - 1):
        k = adj[c[i]]
        if k != []:
            ind = random.randint(0, len(k) - 1)
            c.append(k[ind])
            num.remove(c[i + 1])
            for j in adj[c[i + 1]]:
                adj[j].remove(c[i + 1])
        else:
            c.append(num[int(random.randint(0, len(num) - 1))])
            num.remove(c[i + 1])
            for j in adj[c[i + 1]]:
                adj[j].remove(c[i + 1])
    result = []
    for i in range(0, len(c)):
        j = 0
        while A[j].get_name() != c[i]:
            j += 1
        result.append(A[j])
    return result


def generatePoints(n):
    graph = []
    for i in range(0, n):
        x = random.randrange(0, 100)
        y = random.randrange(0, 100)
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
        weights[i][i] = 0
    for i in range(0, len(graph)):
        for j in range(0, len(graph)):
            if (i != j):
                n1 = (graph[i].get_x() - graph[j].get_x()) * (graph[i].get_x() - graph[j].get_x())
                n2 = (graph[i].get_y() - graph[j].get_y()) * (graph[i].get_y() - graph[j].get_y())
                weights[i][j] = math.sqrt(n1 + n2)
    with open('matrix.txt', 'wb') as f:
        np.savetxt(f, weights, fmt='%.0f')
    return weights


def handlungsreisendenproblem(n):
    graph = generatePoints(n)
    distances = generateDistances(graph)
    t = 0
    P = []
    for i in range(0, 10):
        tupel = []
        perm = np.random.permutation(graph)
        tupel.append(perm)
        tupel.append(zielfunktion(perm, distances))
        P.append(tupel)
    while t <= 2000:
        newP = []
        for i in range(0, 40):
            A = P[random.randint(0, 9)]
            u = np.random.uniform(0, 1)
            if u < 0.3:
                B = P[random.randint(0, 9)]
                A[0] = kantenrekombination(A[0], B[0])
                A[1] = zielfunktion(A[0], distances)
            A[0] = invertierendeMutation(A[0])
            A[1] = zielfunktion(A[0], distances)
            newP.append(A)
        t += 1
        for i in range(0, len(newP)):
            P.append(newP[i])
        P = best(P)
        if t == 500:
            file = open('Generation500.txt', 'w')
            for elem in P[0][0]:
                file.write(str(elem.get_name()))
                file.write(' ')
            file.write('\n')
            file.write(str(P[0][1]))
            file.close()
        if t == 1000:
            file = open('Generation1000.txt', 'w')
            for elem in P[0][0]:
                file.write(str(elem.get_name()))
                file.write(' ')
            file.write('\n')
            file.write(str(P[0][1]))
            file.close()
        if t == 1500:
            file = open('Generation1500.txt', 'w')
            for elem in P[0][0]:
                file.write(str(elem.get_name()))
                file.write(' ')
            file.write('\n')
            file.write(str(P[0][1]))
            file.close()
        if t == 2000:
            file = open('Generation2000.txt', 'w')
            for elem in P[0][0]:
                file.write(str(elem.get_name()))
                file.write(' ')
            file.write('\n')
            file.write(str(P[0][1]))
            file.close()
    return P[0]


r = handlungsreisendenproblem(100)
for i in range(0, 100):
    print(r[0][i].get_name())
print(r[1])
