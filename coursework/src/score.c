#include <math.h>

typedef struct Score {
	product_t product;
	float score;
} product_ext;

product_t most_valued_product(product_t *products, int product_count) {
	product_ext mvp;

	for (unsigned int i = 0; i < product_count; i++) {
		product_t product = products[i];
		product_ext product_score = {
			.product = product,
			.score = (product.stock) // Exclude zero-stock items.
				? sqrt(product.price) * product.discount / product.stock
				: 0
		};
		// TODO: Add debug flag for verbose printing.
		// printf("%s has score of %f\n", product.name, product_score.score);

		// Replace MVP if score is improved
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