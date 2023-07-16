#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>

uint32_t g;

void clear() {  g = 0; }
void set(uint32_t x) { g = x; }
uint32_t get() { return g; }

int main (int argc, char **argv) {
	uint32_t x;
	if (argc != 2) {
		printf("Usage: %s <number>\n",argv[0]);
		exit(0);
	}
	x = (uint32_t)atoi(argv[1]);
	clear();
	printf("clear(): %d\n",get());
	set(x);
	printf("set(x): %d\n",get());	
}
