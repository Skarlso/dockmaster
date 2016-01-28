package main

//TestDB Encapsulates a connection to a database
type TestDB struct {
}

//Save will save a container using test as a storage medium
func (tdb TestDB) Save(c Containers) error {
	return nil
}

//Load will load all the containers using test as a storage medium
func (tdb TestDB) Load() (c Containers, err error) {
	return Containers{}, nil
}

//Delete will delete the containers using test as a storage medium
func (tdb TestDB) Delete(c Containers) error {
	return nil
}
