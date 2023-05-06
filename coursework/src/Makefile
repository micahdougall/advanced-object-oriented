CC = gcc

all: main

product: product.c
	$(CC) product.c -o product

reader: reader.c
	$(CC) reader.c -o reader

printer: printer.c
	$(CC) printer.c -o printer

main: main.c product.c reader.c printer.c
	$(CC) main.c -o main