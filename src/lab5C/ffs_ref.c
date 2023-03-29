#include <stdio.h>
#include <stdlib.h>

typedef unsigned int uint32_t;

uint32_t ffs_ref(uint32_t word) {
   int i = 0;
	int cnt = 0;
   if (!word) return 0;
   for (cnt = 0; cnt < 32; cnt++)
      if (((1 << i++) & word) != 0) return i;
   return 0; /* notreached */
}

int main (int argc, char **argv) {
   if (argc != 2) {
      printf("Usage: %s <number>\n", argv[0]);
      exit(0);
   }
   uint32_t n = atol(argv[1]);
   printf("ffs_ref: first 1 at %d\n", ffs_ref(n));
}

