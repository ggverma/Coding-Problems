class Solution(object):
    def findAllConcatenatedWordsInADict(self, words):
        """
        :type words: List[str]
        :rtype: List[str]
        """
        minLW = sys.maxsize
        for word in words:
            if len(word) < minLW:
                minLW = len(word)
        potentialKeys = set()
        potentialConcatedWords = set()
        maxLK = 0
        for word in words:
            if len(word) >= 2 * minLW:
                potentialConcatedWords.add(word)
            else:
                potentialKeys.add(word)
                if maxLK < len(word):
                    maxLK = len(word)
        # check if potentialConcatedWord is made by potentialKeys
        answer = list()
        for potentialConcatedWord in potentialConcatedWords:
            i = 0
            j = minLW
            while j < maxLK:
                if potentialConcatedWord[i:j+i+1] in potentialKeys:
                    i = j + 1
                    if i == len(potentialConcatedWord):
                        # yes.
                        answer.add(potentialConcatedWord)
                        break;
                    j = minLW
        return answer
