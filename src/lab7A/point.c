#include <stdbool.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>

typedef struct point {
    uint32_t x;
    uint32_t y;
} point;

point ZERO = {0, 0};

// Check whether two points are equal
bool point_eq(const point *p1, const point *p2) {
    return p1->x == p2->x && p1->y == p2-> y;
}

// Allocate and return a new point
point* point_new(uint32_t x, uint32_t y) {
    point* ret = malloc(sizeof(point));
    ret->x = x;
    ret->y = y;
    return ret;
}

// Return a new point containing a copy of `p`
point* point_copy(const point* p) {
    return point_new(p->x, p->y);
}

// Add two points
point* point_add(const point *p1, const point *p2) {
    // Save an addition by checking for zero
    if (point_eq(p1, &ZERO)) {
        return point_copy(p2);
    }

    if (point_eq(p2, &ZERO)) {
        return point_copy(p1);
    }

    return point_new(p1->x + p2->x, p1->y + p2->y);
}

int main (int argc, char **argv) {
	point *p1, *p2, *p3;
	p1 = point_new(11,34);
	p2 = point_new(5,12);
	p3 = point_new(23,16);
	printf("p1=%d,%d p2=%d,%d p3=%d,%d\n",p1->x,p1->y,p2->x,p2->y,p3->x,p3->y);
	p3 = point_add(p1,p2);
	printf("p1=%d,%d p2=%d,%d p3=%d,%d\n",p1->x,p1->y,p2->x,p2->y,p3->x,p3->y);
   p2 = point_copy(p3);	
	printf("p1=%d,%d p2=%d,%d p3=%d,%d\n",p1->x,p1->y,p2->x,p2->y,p3->x,p3->y);
	printf("p2 and p3 equal? %d\n",point_eq(p2,p3));
	printf("p1 and p3 equal? %d\n",point_eq(p1,p3));
}
