from random import randint

class CircularArrayLoop(object):
    def getNextPosition(self, fromIndex, add, endIndex):
        fromIndex += add
        return fromIndex % endIndex

    def isCircular(self, nums):
        """
        :type nums: List[int]
        :rtype: bool
        """
        # numbers left to scan
        left  = len(nums)
        for i in range(0, len(nums)):
            # skip already checked numbers
            if nums[i] == 0:
                continue
            # next number
            j = self.getNextPosition(i, nums[i], len(nums))
            # if it is encountered, this is not a loop
            if nums[j] == 0:
                nums[i] = 0
                left -= 1
                continue
            # last element
            k = i
            # if next is equal to last, violates minimum 1 element constraint. Not a loop
            if j == k:
                nums[j] = 0
                left -= 1
                continue
            # forward or backward loop
            floop = True if nums[i] > 0 else False
            while left != 0:
                if floop and nums[j] < 0:
                    break
                if not floop and nums[j] > 0:
                    break
                if nums[j] == 0 :
                    return True
                m = self.getNextPosition(j, nums[j], len(nums))
                if(m == j):
                    break
                nums[k] = 0
                k = j
                j = m
                left -= 1
                if left == 0:
                    return False
        return False

def generateRandomList():
    l = []
    for i in range(0, randint(5, 50)):
        l.append(randint(-5, 5))
    return l

def main():
    l = generateRandomList()
    print ('List: ', l)
    print(CircularArrayLoop().isCircular(l))

if __name__ == "__main__":
    main()

# Leet Code Challenge
# You are given an array of positive and negative integers. If a number n at an index is positive, then move forward n steps. Conversely, if it's negative (-n), move backward n steps. Assume the first element of the array is forward next to the last element, and the last element is backward next to the first element. Determine if there is a loop in this array. A loop starts and ends at a particular index with more than 1 element along the loop. The loop must be "forward" or "backward'.
#
# Example 1: Given the array [2, -1, 1, 2, 2], there is a loop, from index 0 -> 2 -> 3 -> 0.
#
# Example 2: Given the array [-1, 2], there is no loop.
#
# Note: The given array is guaranteed to contain no element "0".
#
# Can you do it in O(n) time complexity and O(1) space complexity?
