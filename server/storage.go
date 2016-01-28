package main

//Storage defines a storage medium. It could anything that implements this interface
type Storage interface {
	Save(Container) error
	Load() ([]Container, error)
}
