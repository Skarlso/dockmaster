package main

import (
	"io"
	"net/http"
)

func index(w http.ResponseWriter, r *http.Request) {
	io.WriteString(w, "Hello world!")
}

func main() {
	http.HandleFunc("/", index)
	http.ListenAndServe(":9191", nil)
}
