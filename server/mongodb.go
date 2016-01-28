package main

import (
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
	// bulk := co.Bulk()
	//One post is always affiliated to one agent. Thus, it's enough to get the first
	//containers agentID
	db.Remove(bson.M{"agentid": a.AgentID})
	// if err != nil {
	// 	return err
	// }

	err := db.Insert(a)
	if err != nil {
		return err
	}
	// for _, con := range a.Containers {
	// 	bulk.Insert(con)
	// }
	// _, err = bulk.Run()
	return nil
}

// func (mdb MongoDBConnection) removeAllContainersForAgent(agentID string) error {
// 	db := mdb.session.DB("dockmaster").C("containers")
// 	_, err := db.RemoveAll(bson.M{"agentid": agentID})
// 	return err
// }

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
	// for _, con := range c {
	// 	err := db.Remove(con)
	// 	if err != nil {
	// 		return err
	// 	}
	// }
	// return nil
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
