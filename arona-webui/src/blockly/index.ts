import { BlocklyOptions } from "blockly";
import addBlocks from "@/blockly/blocks";
import addMutators from "@/blockly/mutators";
import injectExtensions from "@/blockly/extensions";

const BlocklyConfig: BlocklyOptions = {
  media: "/media/",
  grid: {
    spacing: 25,
    length: 3,
    colour: "#ccc",
    snap: true,
  },
  disable: false,
  toolbox: await getXML("/blockly/toolbox.xml", "toolbox"),
  maxInstances: {},
  theme: "Zelos",
};

async function getXML(url: string, id: string): Promise<HTMLElement> {
  return fetch(url)
    .then((response) => {
      return response.text();
    })
    .then((str) => {
      return new DOMParser().parseFromString(str, "text/xml").getElementById(id) as HTMLElement;
    });
}

export const workspaceBlocks = await getXML("/blockly/workspace.xml", "workspaceBlocks");

export const blocks = JSON.parse(
  await fetch("/blockly/blocks.json").then((response) => {
    return response.text();
  }),
);

export function initBlockly() {
  addBlocks();
  addMutators();
  injectExtensions();
}

export default BlocklyConfig;
