#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "product.c"


unsigned int get_num_records(char *file_name);
void parse_products_from_file(char *file_name, int product_count, product_t *products);