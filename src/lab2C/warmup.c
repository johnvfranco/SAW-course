#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

struct sha512_state_st {
  uint64_t h[8];
  uint64_t Nl, Nh;
  uint8_t p[128];
  unsigned num, md_len;
};

typedef struct sha512_state_st SHA512_CTX;

void printState (SHA512_CTX state) {
	int i;
	printf("h=");
	for (i=0 ; i < 8 ; i++) printf("%ld,",state.h[i]);
	printf("\nNl=%ld, Nh=%ld\n", state.Nl, state.Nh);
	printf("p=");
	for (i=0 ; i < 128 ; i++) printf("%d,",state.p[i]);
	printf("\nnum=%d, md_len=%d\n", state.num, state.md_len);
}

SHA512_CTX *stateManip (SHA512_CTX state) {
	int i;
	SHA512_CTX *s = (SHA512_CTX*)malloc(sizeof(SHA512_CTX));
	for (i=0 ; i < 8 ; i++) s->h[i] = state.h[i];
	s->Nl = state.Nl;
	s->Nh = state.Nh;
	for (i=0 ; i < 128 ; i++) s->p[i] = state.p[i];
	s->num = state.num;
	s->md_len = 12;
	return s;
}

int main (int argc, char **argv) {
   SHA512_CTX stateInit = { {1,2,3,4,5,6,7,8},
		23, 44,
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 },
		66, 43 };

	printState (stateInit);
   SHA512_CTX *s = stateManip (stateInit);
	printState (*s);

}
