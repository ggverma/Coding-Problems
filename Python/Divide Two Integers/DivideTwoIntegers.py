import sys

class DivideTwoIntegers(object):
    def divide(self, dividend, divisor):
        """
        :type dividend: int
        :type divisor: int
        :rtype: int
        """
        if divisor == 0:
            return sys.maxsize
        negate = False
        if dividend < 0:
            negate = True
            dividend = abs(dividend)
        if divisor < 0:
            negate = not negate
            divisor = abs(divisor)
        q = 0
        while dividend >= divisor:
            n = divisor
            s = 1
            while n << 1 < dividend:
                n <<= 1
                s <<= 1
            q += s
            dividend -= n
        return q if not negate else -q

def main():
    dividend = 21
    divisor = 21
    print(DivideTwoIntegers().divide(dividend, divisor))

if __name__ == "__main__":
    main()


# Divide two integers without using multiplication, division and mod operator.
#
# If it is overflow, return MAX_INT.
