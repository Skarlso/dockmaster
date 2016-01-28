package main

import (
	"fmt"
	"time"

	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
)

//Starting mongodb -> mongod --config /usr/local/etc/mongod.conf --fork

//MongoDBConnection Encapsulates a connection to a database
type MongoDBConnection struct {
	session *mgo.Session
}

//Save will save a contaier using mongodb as a storage medium
func (mdb MongoDBConnection) Save(a Agent) error {
	mdb.session = mdb.GetSession()
	defer mdb.session.Close()
	db := mdb.session.DB("dockmaster").C("containers")
	db.Remove(bson.M{"agentid": a.AgentID})

	//Time Format => 2006-01-02T15:04:05Z
	index := mgo.Index{
		Key:         []string{"expireAt"},
		ExpireAfter: 0,
	}

	err := db.EnsureIndex(index)
	if err != nil {
		panic(err)
	}
	a.ExpireAt = time.Now().Add(time.Second * time.Duration(a.ExpireAfterSeconds))
	fmt.Println(a.ExpireAt)
	err = db.Insert(a)
	if err != nil {
		return err
	}
	return nil
}

//Load will load the contaier using mongodb as a storage medium
func (mdb MongoDBConnection) Load() (a []Agent, err error) {
	mdb.session = mdb.GetSession()
	defer mdb.session.Close()
	c := mdb.session.DB("dockmaster").C("containers")

	iter := c.Find(nil).Iter()
	err = iter.All(&a)

	// log.Println(results)
	return a, err
}

//Delete bulk deletes containers
func (mdb MongoDBConnection) Delete(a Agent) error {
	mdb.session = mdb.GetSession()
	defer mdb.session.Close()
	db := mdb.session.DB("dockmaster").C("containers")
	err := db.Remove(bson.M{"agentid": a.AgentID})
	if err != nil {
		return err
	}
	return nil
}

//GetSession return a new session if there is no previous one
func (mdb *MongoDBConnection) GetSession() *mgo.Session {
	if mdb.session != nil {
		return mdb.session.Copy()
	}
	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	session.SetMode(mgo.Monotonic, true)
	return session
}
