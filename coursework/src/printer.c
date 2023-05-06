#include "printer.h"

void print_stock_report(product_t *products, int product_count) {
	printf("\nPrinting stock report for product inventory:\n\n");

	printf(
		// TODO: Add table header/border
		"      %-10s %6s %9s %-3s %-s\n", 
		"Code", "Stock", "Price", "Dsct", "Product"
	);

	for (unsigned int i = 0; i < product_count; i++) {
		print_product(products[i]);
	}
}

void print_product(product_t product) {
	// TODO: Color columns for readibility
	printf(
		"  ->  %-10u %6u %9.2f %3.0f%% %s\n", 
		product.code, product.stock, product.price, 
		product.discount * 100, product.name
	);
}