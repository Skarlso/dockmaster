package main

import "gopkg.in/mgo.v2"

//Starting mongodb -> mongod --config /usr/local/etc/mongod.conf --fork

//MongoDBConnection Encapsulates a connection to a database
type MongoDBConnection struct {
	session *mgo.Session
}

//Save will save a contaier using mongodb as a storage medium
func (mdb MongoDBConnection) Save(c Containers) error {
	mdb.session = mdb.GetSession()
	defer mdb.session.Close()
	co := mdb.session.DB("dockmaster").C("containers")
	var bulk = co.Bulk()
	for con := range c.Containers {
		bulk.Insert(con)
	}
	_, err := bulk.Run()
	return err
}

//Load will load the contaier using mongodb as a storage medium
func (mdb MongoDBConnection) Load() (results Containers, err error) {
	// return []Container{}, nil

	mdb.session = mdb.GetSession()
	defer mdb.session.Close()
	c := mdb.session.DB("dockmaster").C("containers")
	err = c.Find(nil).All(&results)
	return results, err
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
