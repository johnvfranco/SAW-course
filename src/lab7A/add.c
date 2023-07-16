#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

uint16_t add (uint16_t x, uint16_t y) { return x+y; }

int main(int argc, char **argv) {
	if (argc != 3) {
		printf("Usage: %s <number> <number>",argv[0]);
		exit(0);
	}
	uint16_t a = (uint16_t)atoi(argv[1]);
	uint16_t b = (uint16_t)atoi(argv[2]);
	printf("%d + %d = %d\n",a,b,(uint16_t)add(a,b));
}
