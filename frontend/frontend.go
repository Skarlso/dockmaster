package main

import (
	"fmt"
	"html/template"
	"log"
	"net/http"
	"path"
)

//Container a single container
type Container struct {
	AgentID    string `json:"agentid"`
	ID         string `json:"id"`
	Name       string `json:"name"`
	BuildNode  string `json:"node"`
	RunningCmd string `json:"cmd"`
	Port       int    `json:"port"`
}

//Containers represents a running container
type Containers struct {
	Containers []Container `json:"containers"`
}

//Page test page
type Page struct {
	Title string
	Body  []byte
}

func index(w http.ResponseWriter, r *http.Request) {
	p := Page{"Test Title", []byte("testbody")}
	lp := path.Join("templates", "layout.html")
	tmpl, err := template.ParseFiles(lp)
	if err != nil {
		fmt.Fprintf(w, "error occured:"+err.Error())
		return
	}
	log.Println(p)
	tmpl.Execute(w, p)
}

func main() {
	http.HandleFunc("/", index)
	fs := http.FileServer(http.Dir("static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))
	http.ListenAndServe(":9191", nil)
}
