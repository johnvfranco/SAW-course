#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

uint8_t saf (uint8_t x, uint8_t y) {
	if (y == 0) return 1;
	else return x/y;
}

int main(int argc, char **argv) {
	printf("%d\n",saf((uint8_t)atoi(argv[1]), (uint8_t)atoi(argv[2])));
}
