#! usr/bin/python

import sys

def getAnswer(a, N):
    stack = []
    while N >= 1:
        stack.append(N)
        N = N // 2
    X = 1
    while stack:
        X = X * X
        if stack.pop() % 2 != 0:
            X = X * a
    return X

def main(a, N):
    print(a, "^", N ,"=", getAnswer(a, N))

if __name__ == "__main__":
    if len(sys.argv) == 3:
        print("Custom run")
        main(float(sys.argv[1]), int(sys.argv[2]))
    else:
        print("Default run")
        main(3, 7)

# Evaluates a^N is log(N) time.
# Constraint: N is an integer such that N >= 0
