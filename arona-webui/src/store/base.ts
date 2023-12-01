import { defineStore } from "pinia";
import { BaseStoreState } from "./type";

const useBaseStore = defineStore({
  id: "common",
  state: (): BaseStoreState => ({}),
  getters: {},
  actions: {},
  persist: {
    key: "base",
  },
});
export default useBaseStore;
