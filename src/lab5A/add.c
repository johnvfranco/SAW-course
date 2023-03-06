#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

uint32_t add(uint32_t x, uint32_t y) {
    return x + y;
}

int main (int argc, char** argv) {
	uint32_t a = atol(argv[1]);
	uint32_t b = atol(argv[2]);
	printf("%u\n",add(a,b));
}
