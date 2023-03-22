#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

uint32_t add_in(uint32_t *x, uint32_t y) {
    *x += y;
	 return *x;
}

int main (int argc, char** argv) {
	uint32_t a = atol(argv[1]);
	uint32_t b = atol(argv[2]);	
	printf("%u\n",add_in(&a,b));
}

