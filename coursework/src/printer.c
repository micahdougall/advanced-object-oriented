#include "printer.h"
#include "global.h"

#define REPORT_PREVIEW 5

/**
 * print_stock_report() - Prints a report for a list of products.
 * @*products: A pointer to an array of products.
 * @product_count: The number of products in the array.
 * 
 * The report size will depend on the number of products and the options set.
 * 
 * If the verbosity flag is set to true, all products will be printed, else 
 * the following limits are set:
 * 	- <= 10 products -> all products printed
 *	-  > 10 products -> first 5 and last 2 printed
 */
void print_stock_report(product_t *products, unsigned int product_count) {
	printf("\nPrinting stock report for product import:\n\n");

	printf(
		ANSI_COLOR_CYAN_BOLD
		"      %-10s %6s %9s %-3s %-s\n      " 
		"--------------------------------------------------------------------\n"
		ANSI_COLOR_RESET, 
		"Code", "Stock", "Price", "Dsct", "Product"
	);

	if (VERBOSE || product_count <= 9) {
		for (unsigned int i = 0; i < product_count; i++) {
			print_product(products[i]);
		}
	} else {
		for (unsigned int i = 0; i < REPORT_PREVIEW; i++) {
			print_product(products[i]);
		}
		printf(
			ANSI_BOLD_WHITE "\n     **** %u products not printed ***\n\n"
			ANSI_COLOR_RESET, 
			product_count - (REPORT_PREVIEW + 1)
		);
		print_product(products[product_count - 1]);
	}
}

/**
 * print_product() - Prints a single product in the required format.
 * @product: The data for a product type (product_t).
 */
void print_product(product_t product) {
	printf(
		ANSI_COLOR_GREEN "  ->  "
		ANSI_BOLD_WHITE "%-10u "
		ANSI_COLOR_RESET "%6u %9.2f"
		ANSI_COLOR_GREEN "£" 
		ANSI_COLOR_MAGENTA "%3.0f%% "
		ANSI_COLOR_GREEN "%s\n"
		ANSI_COLOR_RESET, 
		product.code, product.stock, product.price, 
		product.discount * 100, product.name
	);
}