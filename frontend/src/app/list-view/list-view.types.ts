export type Board = {
  id: number;
  name: string;
  columns: Column[];
}

export type Column = {
  id: number;
  name: string;
  tasks: Task[];
}

export type Task = {
  id: number;
  title: string;
}
