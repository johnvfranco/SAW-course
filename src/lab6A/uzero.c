#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>

uint8_t usaf (uint8_t x, uint8_t y) {
   return x/y;
}

int main(int argc, char **argv) {
	printf("%d\n",usaf((uint8_t)atoi(argv[1]), (uint8_t)atoi(argv[2])));
}
