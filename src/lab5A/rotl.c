#include <stdint.h>

uint32_t ROTL(uint32_t x, uint8_t r) {
    r = r % 32;
    uint32_t bottom = x >> (32 - r);
    uint32_t top = x << r;
    return bottom | top;
}
