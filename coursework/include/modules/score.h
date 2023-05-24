#include <math.h>

/**
 * Score - Stores a product along with its calculated score.
 * @product: The product.
 * @score: Its score.
 * 
 * Simplified with typedef product_ext.
 */
typedef struct Score {
	product_t *product;
	float score;
} product_ext;

product_t* most_valued_product(product_t* products, int product_count);