package main

import (
	"encoding/json"
	"io/ioutil"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

//APIVERSION Is the current API version
const APIVERSION = "1"

//APIBASE Defines the API base URI
const APIBASE = "api/" + APIVERSION

var mdb Storage
var config Config

//Container a single container
type Container struct {
	ID         string `json:"id"`
	Name       string `json:"name"`
	RunningCmd string `json:"command"`
	Port       string `json:"port"`
}

//Agent post data from an agent with ID and containers it has.
type Agent struct {
	AgentID            string      `json:"agentid"`
	ExpireAt           time.Time   `json:"expireAt,omitempty"`
	ExpireAfterSeconds int         `json:"expireAfterSeconds"`
	Containers         []Container `json:"containers"`
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
		v1.POST("/add", addContainers)
		v1.POST("/delete", deleteContainers)
	}
	router.Run(":8989")
}

//index a humble welcome to a new user
func listContainers(c *gin.Context) {
	agents, err := mdb.Load()
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorResponse{"error while loading containers: " + err.Error()})
		return
	}
	c.JSON(http.StatusOK, agents)
}

func addContainers(c *gin.Context) {
	agent := Agent{}
	err := c.BindJSON(&agent)
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorResponse{"error binding json: " + err.Error()})
		return
	}
	err = mdb.Save(agent)
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorResponse{"error while saving container: " + err.Error()})
		return
	}

	c.JSON(http.StatusOK, Message{"Containers successfully saved."})

}
func deleteContainers(c *gin.Context) {
	agent := Agent{}
	err := c.BindJSON(&agent)
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorResponse{"error binding json: " + err.Error()})
		return
	}
	err = mdb.Delete(agent)
	if err != nil {
		c.JSON(http.StatusBadRequest, ErrorResponse{"error while deleting containers: " + err.Error()})
		return
	}
	c.JSON(http.StatusOK, Message{"Containers successfully removed."})
}
