package main

//Storage defines a storage medium. It could anything that implements this interface
type Storage interface {
	Save(Containers) error
	Load() (Containers, error)
	Delete(Containers) error
}
