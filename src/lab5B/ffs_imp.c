#include <stdio.h>
#include <stdlib.h>

typedef unsigned int uint32_t;

uint32_t ffs_imp(uint32_t word) {
    char n = 1;
    if (!(word & 0xffff)) { n += 16; word >>= 16; }
    if (!(word & 0x00ff)) { n += 8;  word >>= 8; }
    if (!(word & 0x000f)) { n += 4;  word >>= 4; }
    if (!(word & 0x0003)) { n += 2;  word >>= 2; }
    return (word) ? (n+((word+1) & 0x01)) : 0;
}

int main (int argc, char **argv) {
   if (argc != 2) {
      printf("Usage: %s <number>\n", argv[0]);
      exit(0);
   }
   uint32_t n = atol(argv[1]);
   printf("ffs_imp: first 1 at %d\n", ffs_imp(n));
}
