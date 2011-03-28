--
--	Just test fmax of memory with registered input and output
--
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;


entity memfmax is
	port (
		clk : std_logic;
		din : in std_logic_vector(15 downto 0);
		rd_addr, wr_addr : in std_logic_vector(7 downto 0);
		wr : in std_logic;
		dout : out std_logic_vector(15 downto 0)
	);
end memfmax;

architecture rtl of memfmax is

	signal clk_int : std_logic;
	
		-- the data ram
	constant nwords : integer := 2 ** 8;
	type ram_type is array(0 to nwords-1) of std_logic_vector(15 downto 0);
	signal ram : ram_type;

	signal wr_reg : std_logic;
	signal wrdata, rddata : std_logic_vector(15 downto 0);
	signal wraddr, rdaddr : std_logic_vector(7 downto 0);

	

begin

-- 	-- Altera PLL
-- 	-- assume 20 or 50 MHz input clock
-- 	pll_inst : entity work.pll generic map(
-- 		multiply_by => 20, -- 300 MHz
-- 		divide_by => 1
-- 	)
-- 	port map (
-- 		inclk0	 => clk,
-- 		c0	 => clk_int
-- 	);
	
	-- Xilinx DCM
	-- input clock is 50 MHz
	-- let's go for 200 MHz ;-)
	pll_inst : entity work.sp3epll generic map(
		multiply_by => 8,
		divide_by => 1
	)
	port map (
		CLKIN_IN => clk,
		RST_IN => '0',
		CLKFX_OUT => clk_int,
		CLKIN_IBUFG_OUT => open,
		CLK0_OUT => open,
		LOCKED_OUT => open
	);
	
process (clk_int)
begin
	if rising_edge(clk_int) then
		if wr_reg='1' then
			ram(to_integer(unsigned(wraddr))) <= wrdata;
		end if;
		rddata <= ram(to_integer(unsigned(rdaddr)));
		wrdata <= din;
		rdaddr <= rd_addr;
		wraddr <= wr_addr;
		wr_reg <= wr;
		dout <= rddata;
	end if;
end process;

end rtl;
