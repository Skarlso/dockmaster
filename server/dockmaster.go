package main

import (
	"encoding/json"
	"io/ioutil"
	"net/http"

	"github.com/gin-gonic/gin"
)

//APIVERSION Is the current API version
const APIVERSION = "1"

//APIBASE Defines the API base URI
const APIBASE = "api/" + APIVERSION

var mdb Storage
var config Config

//Container represents a running container
type Container struct {
	Name       string `json:"name"`
	BuildNode  string `json:"node"`
	RunningCmd string `json:"cmd"`
	Port       int    `json:"port"`
}

//Config global configuration of the application
type Config struct {
	Storage string `json:"Storage"`
	DBURL   string `json:"DBURL"`
}

func getConfiguration() (c Config) {
	dat, err := ioutil.ReadFile("config.json")
	if err != nil {
		panic(err)
	}

	if err = json.Unmarshal(dat, &c); err != nil {
		panic(err)
	}

	return
}

func init() {
	config := getConfiguration()

	switch config.Storage {
	case "mongodb":
		mdb = MongoDBConnection{}
	case "test":
		mdb = TestDB{}
	}
}

// The main function which starts the rpg
func main() {
	router := gin.Default()
	v1 := router.Group(APIBASE)
	{
		v1.GET("/list", listContainers)
		v1.POST("/add", addContainer)
	}
	router.Run(":8989")
}

//index a humble welcome to a new user
func listContainers(c *gin.Context) {
	m := Message{}
	m.Message = "Welcome to my RPG"
	c.JSON(http.StatusOK, m)
}

func addContainer(c *gin.Context) {

}
