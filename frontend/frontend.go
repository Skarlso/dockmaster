package main

import (
	"encoding/json"
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
	RunningCmd string `json:"command"`
	Port       string `json:"port"`
}

func index(w http.ResponseWriter, r *http.Request) {
	resp, _ := http.Get("http://localhost:8989/api/1/list")
	con := []Container{}
	decoder := json.NewDecoder(resp.Body)
	err := decoder.Decode(&con)
	if err != nil {
		fmt.Fprintf(w, "error occured:"+err.Error())
		return
	}
	log.Println(con)

	lp := path.Join("templates", "layout.html")
	tmpl, err := template.ParseFiles(lp)
	if err != nil {
		fmt.Fprintf(w, "error occured:"+err.Error())
		return
	}
	tmpl.Execute(w, con)
}

func main() {
	http.HandleFunc("/", index)
	fs := http.FileServer(http.Dir("static"))
	http.Handle("/static/", http.StripPrefix("/static/", fs))
	http.ListenAndServe(":9191", nil)
}
