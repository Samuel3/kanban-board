export type Board = {
  id: string
  name: string;
  columns: Column[];
}

export type Column = {
  id: string
  name: string;
  tasks: Task[];
}

export type Task = {
  id: string
  title: string;
}
