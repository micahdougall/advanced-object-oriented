#include "main.h"

int main(int argc, char const *argv[])
{
	char *file_name = "T5-Products-10.txt";
	unsigned int *product_count = (unsigned int *) malloc(sizeof(unsigned int *));

	product_t *products = parse_products_from_file(file_name, product_count);

	print_stock_report(products, *product_count);

	printf("\nMost valued product is...");
	product_t mvp = most_valued_product(products, *product_count);
	printf("%s\n", mvp.name);

	return 0;
}
