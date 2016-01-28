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
	bulk := co.Bulk()
	for _, con := range c.Containers {
		// log.Println(con)
		bulk.Insert(con)
	}
	_, err := bulk.Run()
	return err
}

//Load will load the contaier using mongodb as a storage medium
func (mdb MongoDBConnection) Load() (results Containers, err error) {
	mdb.session = mdb.GetSession()
	defer mdb.session.Close()
	c := mdb.session.DB("dockmaster").C("containers")

	iter := c.Find(nil).Iter()
	err = iter.All(&results.Containers)

	// log.Println(results)
	return results, err
}

//Update bulk updates containers
func (mdb MongoDBConnection) Update(c Containers) error {
	return nil
}

//Delete bulk deletes containers
func (mdb MongoDBConnection) Delete(c Containers) error {
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
