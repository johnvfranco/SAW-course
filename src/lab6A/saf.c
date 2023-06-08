#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <stdlib.h>

uint8_t* saf (uint8_t *a, uint8_t b[10]) {
	uint8_t i,x;
	if (*a < 0 || *a > 9) *a = 0;
	else *a = b[*a];
	return a;
}

int main (int argc, char **argv) {
	uint8_t array[10];
	uint8_t i, idx;

	for (i=2 ; i < 12 ; i++) array[i-2] = (uint8_t)atoi(argv[i]);
	idx = (uint8_t)atoi(argv[1]);
	uint8_t* out = saf(&idx, array);
	printf("%d\n", *out);
}
