#include <math.h>

/**
 * Score - Stores a product along with its calculated score.
 * @product: The product.
 * @score: Its score.
 * 
 * Simplified with typedef product_ext.
 */
typedef struct Score {
	product_t product;
	float score;
} product_ext;


/**
 * most_valued_product() - Returns the most desired product from an array.
 * @*products: The array of products to analyse.
 * product_count: The number of products in the array.
 * 
 * A score is calculated for each product in the array using three factors:
 *  - stock: less is better
 *  - price: higher is better
 *  - discount: higher is better
 *  
 * Returns: The product with the best score.
 */
product_t most_valued_product(product_t *products, int product_count) {
	product_ext mvp;


	print_if(
		VERBOSE, 
		ANSI_COLOR_CYAN_BOLD "\n\n%s\n" ANSI_COLOR_RESET, "All scores:"
	);
	for (unsigned int i = 0; i < product_count; i++) {
		product_t product = products[i];
		product_ext product_score = {
			.product = product,
			.score = (product.stock) // Exclude zero-stock items.
				? sqrt(product.price) * product.discount / product.stock
				: 0
		};

		// Verbose print out for each product score.
		if (VERBOSE) {
			printf(
				"%s has score of "
				ANSI_BOLD_WHITE "%f\n"
				ANSI_COLOR_RESET,
				product.name, product_score.score
			);
		}

		// For each product, replace MVP if score has improved.
		mvp = (product_score.score > mvp.score) ? product_score : mvp;
	}
	printf(
		"\nMost valued product is "
		ANSI_COLOR_MAGENTA "%s "
		ANSI_COLOR_RESET "with a score of "
		ANSI_BOLD_WHITE "%f\n\n"
		ANSI_COLOR_RESET, 
		mvp.product.name, 
		mvp.score
	);
	return mvp.product;
}