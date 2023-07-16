#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    uint32_t *x;
} s;

uint32_t add_indirect(s *o) {
    return (o->x)[0] + (o->x)[1];
}

void set_indirect(s *o) {
    (o->x)[0] = 12;
    (o->x)[1] = 6;
}

s *s_id(s *o) {
    return o;
}

int main (int argc, char **argv) {
	s p;
	uint32_t q[2];
	p.x = q;
	set_indirect(&p);
	printf("(%d,%d)\n", p.x[0], p.x[1]);
	printf("add_indirect=%d\n", add_indirect(&p));
}
