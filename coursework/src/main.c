#include "main.h"

int main(int argc, char const *argv[])
{
	char *file_name = "T5-Products-10.txt";

	unsigned int product_count = get_num_records(file_name);

	printf("%s contains %d records.\n\n", file_name, product_count);

	product_t *products = (product_t*) malloc(sizeof(product_t) * product_count);
	printf("Memory allocated at %p...", products);

	parse_products_from_file(file_name, product_count, products);

	print_stock_report(products, product_count);

	printf("\nMost valued product is...");
	product_t mvp = most_valued_product(products, product_count);
	printf("%s\n", mvp.name);

	return 0;
}
