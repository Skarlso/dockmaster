package main

//TestDB Encapsulates a connection to a database
type TestDB struct {
}

//Save will save a contaier using test as a storage medium
func (tdb TestDB) Save(c Containers) error {
	return nil
}

//Load will load the contaier using test as a storage medium
func (tdb TestDB) Load() (c Containers, err error) {
	return Containers{}, nil
}
