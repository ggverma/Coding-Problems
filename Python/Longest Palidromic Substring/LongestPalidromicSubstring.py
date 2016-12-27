# Leet Code Challenge
#
# Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.

# Manacher's Algorithm

from math import ceil

class LongestPalidromicSubstring(object):
    def preProcess(self, s):
        t = ""
        for c in s:
            t += '#' + c
        t += '#'
        return t

    def longestPalindrome(self, s):
        """
        :type s: str
        :rtype: str
        """
        t = self.preProcess(s)
        p = [0]
        c = r = 0
        for i in range(1, len(t) - 1):

            i_mirror = 2 * c - i
            # print(i, c, r, i_mirror)
            p.append(0 if r <= i else min(r - i, p[i_mirror]))

            while i + 1 + p[i] < len(t) and i - 1 - p[i] >= 0 and t[i + 1 + p[i]] == t[i - 1 - p[i]]:
                p[i] += 1

            if i + p[i] > r:
                c = i
                r = i + p[i]

        centerIntex = maxLength = 0
        for i in range(1, len(t) - 1):
            if p[i] > maxLength:
                maxLength = p[i]
                centerIntex = i
        # print(t)
        # print(p)

        del p
        return s[ceil((centerIntex - 1 - maxLength) / 2): ceil((centerIntex - 1 - maxLength) / 2) + maxLength]

def main():
    string = "babad"
    print(LongestPalidromicSubstring().longestPalindrome(string))

if __name__ == "__main__":
    main()
