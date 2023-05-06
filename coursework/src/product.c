#define PRODUCT_NAME_LENGTH 100

typedef struct Product {
	unsigned int code;
	unsigned int stock;
	float price;
	float discount;
	char name[PRODUCT_NAME_LENGTH];
} product_t;