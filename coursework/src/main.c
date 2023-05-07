#include "main.h"

int main(int argc, char *argv[])
{
	if (argc == 1) {
		printf("Missing required argument for input file.\n");
		exit(EXIT_FAILURE);
	}

	char *file_name = argv[1];
	unsigned int *product_count = (unsigned int *) malloc(sizeof(unsigned int *));

	product_t *products = parse_products_from_file(file_name, product_count);

	print_stock_report(products, *product_count);

	product_t mvp = most_valued_product(products, *product_count);

	// Create root node
	trie_node root_node = { 
		.children = malloc(sizeof(trie_node *) * 10)
	};
	// printf(" -> root_node is %p\n", &root_node);

	// printf("\nInserting into trie: %s\n", mvp.name);
	// insert_into_trie(&root_node, &mvp);
	
	// printf("\nInserting into trie: %s\n", products[1].name);
	// insert_into_trie(&root_node, &products[1]);
	// insert_into_trie(&root_node, &products[3]);
	// insert_into_trie(&root_node, &products[8]);

	printf("\nInserting %u products in database...\n\n", *product_count);
	for (unsigned int i = 0; i < *product_count; i++) {
		insert_into_trie(&root_node, &products[i]);
	}

	// char *route = (char *) malloc(sizeof(char));
	printf("\nPrinting trie...\n");
	print_trie(&root_node);


	unsigned int search_code = 126381609;
	product_t *required_product = lookup_product(&root_node, search_code);

	if (required_product != NULL) {
		printf("\n\nProduct found with code %u\n", search_code);
		print_product(*required_product);
	} else {
		printf("No product found with code %u!\n", search_code);
	}


	return EXIT_SUCCESS;
}
