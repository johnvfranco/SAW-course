#include <stdio.h>
#include <stdlib.h>

typedef unsigned int uint32_t;

/* note: word & -word: example: word=01101000 -word=10011000 so word & -word is 00001000
   A length-n deBruijn sequence where n is a power of 2 is a cycle sequence of n 0's and
   1's such that every 0-1 sequence of length lg(n) occurs exactly once as a contiguous
   substring.  For example a length-8 de Bruijn sequence is 00011101 because each 3-bit
   number occurs exactly once as a contiguous substring: 
      000 (0 - 1st from left), 
      001 (1 - 2nd from left), 
      011 (3 - 3rd from left), 
      111 (7 - 4th from left), 
      110 (6 - 5th from left),
      101 (5 - 6th from left)
      010 (2 - 7th from left and wrapping),
      100 (4 - 8th from left and wrapping)
   Compute a hash function h(x) = (x * debruijn) >> (n - lg(n))
   Example: for length-n sequence debruijn=00011101, 8 - lg(8) = 5.
     h(x)   index
     000     0      suppose input is 00010000
     001     1      multiply by 0001101 to get 0000000111010000
     010     6      retain low order 8 bits: 11010000 then >> 5 to get 110
     011     2      110 in table says 4 which is where the 1 is (from the right).
     100     7      suppose input is 00000100
     101     5      multiply by 0001101 to get 01110100 then >> 5 to get 011
     110     4      011 in the table is 2 which is where the 1 is
     111     3

    The number below 0x76be629 = 00000111011010111110011000101001 = deBruijn sequence
*/
uint32_t ffs_mus (uint32_t word) {
  static const char debruijn32[32] = {
    0, 1, 23, 2, 29, 24, 19, 3, 30, 27, 25, 11, 20, 8, 4, 13,
    31, 22, 28, 18, 26, 10, 7, 12, 21, 17, 9, 6, 16, 5, 15, 14
  };
  return word ? debruijn32[(word & -word)*0x076be629 >> 27]+1 : 0;
}

int main (int argc, char **argv) {
   if (argc != 2) {
      printf("Usage: %s <number>\n", argv[0]);
      exit(0);
   }
   uint32_t n = atol(argv[1]);
   printf("ffs_mus: first 1 at %d\n", ffs_mus(n));
}
