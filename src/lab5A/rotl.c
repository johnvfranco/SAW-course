#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

uint32_t ROTL(uint32_t x, uint8_t r) {
    r = r % 32;
    uint32_t bottom = x >> (32 - r);
    uint32_t top = x << r;
    return bottom | top;
}

int main (int argc, char** argv) {
	uint32_t a = atol(argv[1]);
	uint32_t b = atol(argv[2]);
	printf("%u\n",ROTL(a,b));
}
