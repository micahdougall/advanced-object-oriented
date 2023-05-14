#define PRODUCT_NAME_LENGTH 100


/**
 * Product - Stores data associated with a product.
 * @code: A unique code for the product,
 * @stock: The number of items left in stock.
 * @price: The standard price.
 * @discount: The max discount that can be applied.
 * @name: The product's name.
 * 
 * Simplified with typedef product_t.
 */
typedef struct Product {
	unsigned int code;
	unsigned int stock;
	float price;
	float discount;
	char name[PRODUCT_NAME_LENGTH];
} product_t;