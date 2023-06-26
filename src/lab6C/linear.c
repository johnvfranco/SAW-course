#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>

// Input should be increasing set of distinct numbers
uint8_t inputOK (uint16_t elem[]) {
   int i;
   for (i=0 ; i < 232 ; i++) if (elem[i] >= elem[i+1]) return 0;
	return 1;	
}

uint8_t lin_search (uint16_t key, uint16_t xs[]) {
   uint8_t i;
   if (inputOK(xs) == 0) return 255;
   for (i=0 ; i < 233 ; i++) if (key == xs[i]) return i;
   return 255;
}

int main (int argc, char **argv) {
}
