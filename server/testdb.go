package main

//TestDB Encapsulates a connection to a database
type TestDB struct {
}

//Save will save a container using test as a storage medium
func (tdb TestDB) Save(a Agent) error {
	return nil
}

//Load will load all the containers using test as a storage medium
func (tdb TestDB) Load() (a []Agent, err error) {
	return []Agent{}, nil
}

//Delete will delete the containers using test as a storage medium
func (tdb TestDB) Delete(a Agent) error {
	return nil
}
